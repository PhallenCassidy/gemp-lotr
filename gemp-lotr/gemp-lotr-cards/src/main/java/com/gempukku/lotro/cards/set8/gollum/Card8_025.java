package com.gempukku.lotro.cards.set8.gollum;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDeckEffect;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: Siege of Gondor
 * Side: Shadow
 * Culture: Gollum
 * Twilight Cost: 6
 * Type: Minion • Spider
 * Strength: 8
 * Vitality: 8
 * Site: 8
 * Game Text: Fierce. When you play Shelob, you may play a [GOLLUM] possession from your draw deck. Shelob is
 * strength +3 for each minion stacked on a [GOLLUM] possession.
 */
public class Card8_025 extends AbstractMinion {
    public Card8_025() {
        super(6, 8, 8, 8, Race.SPIDER, Culture.GOLLUM, "Shelob", "Eater of Light", true);
        addKeyword(Keyword.FIERCE);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new StrengthModifier(self, self, null,
                        new Evaluator() {
                            @Override
                            public int evaluateExpression(LotroGame game, PhysicalCard cardAffected) {
                                int count = 0;
                                final Collection<PhysicalCard> possessions = Filters.filterActive(game, Culture.GOLLUM, CardType.POSSESSION);
                                for (PhysicalCard possession : possessions) {
                                    count += Filters.filter(game.getGameState().getStackedCards(possession), game, CardType.MINION).size();
                                }
                                return count * 3;
                            }
                        }));
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.played(game, effectResult, self)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendEffect(
                    new ChooseAndPlayCardFromDeckEffect(playerId, Culture.GOLLUM, CardType.POSSESSION));
            return Collections.singletonList(action);
        }
        return null;
    }
}
