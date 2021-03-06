package com.gempukku.lotro.cards.set4.dwarven;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.StackTopCardsFromDeckEffect;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 0
 * Type: Event
 * Game Text: Fellowship: Stack the top 2 cards from your draw deck on a [DWARVEN] condition that has a card already stacked on it.
 */
public class Card4_043 extends AbstractEvent {
    public Card4_043() {
        super(Side.FREE_PEOPLE, 0, Culture.DWARVEN, "Come Here Lad", Phase.FELLOWSHIP);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose DWARVEN condition", Culture.DWARVEN, CardType.CONDITION, Filters.hasStacked(Filters.any)) {
                    @Override
                    protected void cardSelected(LotroGame game, PhysicalCard card) {
                        action.insertEffect(
                                new StackTopCardsFromDeckEffect(self, playerId, 2, card));
                    }
                });
        return action;
    }
}
