package com.gempukku.lotro.cards.set3.isengard;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.ChooseAndHealCharactersEffect;
import com.gempukku.lotro.logic.effects.RevealRandomCardsFromHandEffect;

import java.util.List;

/**
 * Set: Realms of Elf-lords
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 2
 * Type: Event
 * Game Text: Regroup: Reveal a card at random from the Free Peoples player's hand. Heal X [ISENGARD] minions, where X
 * is the twilight cost of the card revealed.
 */
public class Card3_051 extends AbstractEvent {
    public Card3_051() {
        super(Side.SHADOW, 2, Culture.ISENGARD, "Coming for the Ring", Phase.REGROUP);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, LotroGame game, PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new RevealRandomCardsFromHandEffect(playerId, game.getGameState().getCurrentPlayerId(), self, 1) {
                    @Override
                    protected void cardsRevealed(List<PhysicalCard> revealedCards) {
                        if (revealedCards.size() > 0) {
                            PhysicalCard revealedCard = revealedCards.get(0);
                            int twilightCost = revealedCard.getBlueprint().getTwilightCost();
                            action.appendEffect(
                                    new ChooseAndHealCharactersEffect(action, playerId, twilightCost, twilightCost, Culture.ISENGARD, CardType.MINION));
                        }
                    }
                });
        return action;
    }
}
