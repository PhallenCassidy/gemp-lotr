package com.gempukku.lotro.cards.set12.dwarven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDiscardEffect;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Black Rider
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 2
 * Type: Condition • Support Area
 * Game Text: Each Dwarf is damage +1 for each possession he bears. Fellowship: Discard this condition to play
 * a [DWARVEN] possession from your discard pile.
 */
public class Card12_012 extends AbstractPermanent {
    public Card12_012() {
        super(Side.FREE_PEOPLE, 2, CardType.CONDITION, Culture.DWARVEN, "Proud and Able", null, true);
    }

    @Override
    public java.util.List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return java.util.Collections.singletonList(new KeywordModifier(self, Race.DWARF, null, Keyword.DAMAGE,
                new Evaluator() {
                    @Override
                    public int evaluateExpression(LotroGame game, PhysicalCard cardAffected) {
                        return Filters.countActive(game, CardType.POSSESSION, Filters.attachedTo(cardAffected));
                    }
                }));
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)
                && PlayConditions.canSelfDiscard(self, game)
                && PlayConditions.canPlayFromDiscard(playerId, game, Culture.DWARVEN, CardType.POSSESSION)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfDiscardEffect(self));
            action.appendEffect(
                    new ChooseAndPlayCardFromDiscardEffect(playerId, game, Culture.DWARVEN, CardType.POSSESSION));
            return Collections.singletonList(action);
        }
        return null;
    }
}
