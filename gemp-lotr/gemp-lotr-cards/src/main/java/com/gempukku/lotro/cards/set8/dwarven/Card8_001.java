package com.gempukku.lotro.cards.set8.dwarven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.DrawCardsEffect;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.condition.InitiativeCondition;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Siege of Gondor
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 1
 * Type: Condition • Support Area
 * Game Text: While you have initiative, each Dwarf is damage +1. Fellowship: Spot a Dwarf who is damage +X to draw
 * X cards. Discard this condition.
 */
public class Card8_001 extends AbstractPermanent {
    public Card8_001() {
        super(Side.FREE_PEOPLE, 1, CardType.CONDITION, Culture.DWARVEN, "Aggression");
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new KeywordModifier(self, Race.DWARF, new InitiativeCondition(Side.FREE_PEOPLE), Keyword.DAMAGE, 1));
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(final String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)
                && PlayConditions.canSpot(game, Race.DWARF, Keyword.DAMAGE)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseActiveCardEffect(self, playerId, "Choose a Dwarf", Race.DWARF, Keyword.DAMAGE) {
                        @Override
                        protected void cardSelected(LotroGame game, PhysicalCard card) {
                            int count = game.getModifiersQuerying().getKeywordCount(game, card, Keyword.DAMAGE);
                            action.insertEffect(
                                    new DrawCardsEffect(action, playerId, count));
                        }
                    }
            );
            action.appendEffect(
                    new SelfDiscardEffect(self));
            return Collections.singletonList(action);
        }
        return null;
    }
}
