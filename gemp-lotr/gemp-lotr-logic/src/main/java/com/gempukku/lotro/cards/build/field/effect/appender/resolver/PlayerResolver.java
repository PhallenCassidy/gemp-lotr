package com.gempukku.lotro.cards.build.field.effect.appender.resolver;

import com.gempukku.lotro.cards.build.CardGenerationEnvironment;
import com.gempukku.lotro.cards.build.FilterableSource;
import com.gempukku.lotro.cards.build.InvalidCardDefinitionException;
import com.gempukku.lotro.cards.build.PlayerSource;
import com.gempukku.lotro.cards.build.field.effect.EffectAppender;
import com.gempukku.lotro.cards.build.field.effect.appender.AbstractEffectAppender;
import com.gempukku.lotro.common.Filterable;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.UnrespondableEffect;

import java.util.Collection;

public class PlayerResolver {
    public static PlayerSource resolvePlayer(String type, CardGenerationEnvironment environment) throws InvalidCardDefinitionException {
        if (type.equals("owner"))
            return (playerId, game, self, effectResult, effect) -> self.getOwner();
        else if (type.equals("shadowPlayer"))
            return (playerId, game, self, effectResult, effect) -> GameUtils.getFirstShadowPlayer(game);
        else if (type.equals("fp"))
            return ((playerId, game, self, effectResult, effect) -> game.getGameState().getCurrentPlayerId());
        else if (type.startsWith("owner(") && type.endsWith(")")) {
            String filter = type.substring(type.indexOf("(") + 1, type.lastIndexOf(")"));
            final FilterableSource filterableSource = environment.getFilterFactory().generateFilter(filter);
            return (playerId, game, self, effectResult, effect) -> {
                final Filterable filterable = filterableSource.getFilterable(playerId, game, self, effectResult, effect);
                // TODO SUPER messy
                if (filterable instanceof PhysicalCard) {
                    return ((PhysicalCard) filterable).getOwner();
                }
                throw new RuntimeException("Unable to resolve card from filter");
            };
        }
        throw new InvalidCardDefinitionException("Unable to resolve player resolver of type: " + type);
    }

    public static EffectAppender resolvePlayer(String type, String memory, CardGenerationEnvironment environment) throws InvalidCardDefinitionException {
        if (type.equals("owner")) {
            return new AbstractEffectAppender() {
                @Override
                protected Effect createEffect(CostToEffectAction action, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                    return new UnrespondableEffect() {
                        @Override
                        protected void doPlayEffect(LotroGame game) {
                            action.setValueToMemory(memory, self.getOwner());
                        }
                    };
                }

                @Override
                public boolean isPlayableInFull(CostToEffectAction action, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                    return true;
                }
            };
        } else if (type.startsWith("owner(") && type.endsWith(")")) {
            String filter = type.substring(type.indexOf("(") + 1, type.lastIndexOf(")"));
            final FilterableSource filterableSource = environment.getFilterFactory().generateFilter(filter);
            return new AbstractEffectAppender() {
                @Override
                public boolean isPlayableInFull(CostToEffectAction action, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                    return true;
                }

                @Override
                protected Effect createEffect(CostToEffectAction action, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                    return new UnrespondableEffect() {
                        @Override
                        protected void doPlayEffect(LotroGame game) {
                            final Filterable filterable = filterableSource.getFilterable(playerId, game, self, effectResult, effect);
                            if (filterable instanceof PhysicalCard) {
                                action.setValueToMemory(memory, ((PhysicalCard) filterable).getOwner());
                            } else {
                                final Collection<PhysicalCard> physicalCards = Filters.filterActive(game, filterable);
                                PhysicalCard card = physicalCards.iterator().next();
                                action.setValueToMemory(memory, card.getOwner());
                            }
                        }
                    };
                }
            };
        }
        throw new InvalidCardDefinitionException("Unable to resolve player resolver of type: " + type);
    }
}