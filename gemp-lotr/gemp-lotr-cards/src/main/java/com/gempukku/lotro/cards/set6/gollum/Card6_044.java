package com.gempukku.lotro.cards.set6.gollum;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.AbstractActionProxy;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseActionProxyEffect;
import com.gempukku.lotro.logic.effects.AddUntilEndOfTurnModifierEffect;
import com.gempukku.lotro.logic.effects.WoundCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPutCardFromDiscardIntoHandEffect;
import com.gempukku.lotro.logic.modifiers.MoveLimitModifier;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Ents of Fangorn
 * Side: Free
 * Culture: Gollum
 * Twilight Cost: 5
 * Type: Event
 * Game Text: Regroup: Exert Smeagol 3 times and discard him to wound each minion. The move limit for this turn is +1.
 * If the fellowship moves, each Shadow player may take up to 3 Shadow cards into hand from his or her discard pile.
 */
public class Card6_044 extends AbstractEvent {
    public Card6_044() {
        super(Side.FREE_PEOPLE, 5, Culture.GOLLUM, "Safe Paths", Phase.REGROUP);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canExert(self, game, 3, Filters.smeagol)
                && PlayConditions.canDiscardFromPlay(self, game, Filters.smeagol);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, final LotroGame game, final PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseAndExertCharactersEffect(action, playerId, 1, 1, 3, Filters.smeagol));
        action.appendCost(
                new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, Filters.smeagol));
        action.appendEffect(
                new WoundCharactersEffect(self, CardType.MINION));
        action.appendEffect(
                new AddUntilEndOfTurnModifierEffect(
                        new MoveLimitModifier(self, 1)));
        action.appendEffect(
                new AddUntilEndOfPhaseActionProxyEffect(
                        new AbstractActionProxy() {
                            @Override
                            public List<? extends OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame lotroGame, EffectResult effectResult) {
                                if (TriggerConditions.moves(game, effectResult)
                                        && !lotroGame.getGameState().getCurrentPlayerId().equals(playerId)) {
                                    OptionalTriggerAction action = new OptionalTriggerAction(self);
                                    action.setVirtualCardAction(true);
                                    action.appendEffect(
                                            new ChooseAndPutCardFromDiscardIntoHandEffect(action, playerId, 0, 3, Side.SHADOW));
                                    return Collections.singletonList(action);
                                }
                                return null;
                            }
                        }));
        return action;
    }
}
