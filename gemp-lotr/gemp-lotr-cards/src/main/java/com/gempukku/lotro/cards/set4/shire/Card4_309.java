package com.gempukku.lotro.cards.set4.shire;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddBurdenEffect;
import com.gempukku.lotro.logic.effects.ChooseAndHealCharactersEffect;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Shire
 * Twilight Cost: 0
 * Type: Event
 * Game Text: Fellowship: Add a burden to heal a Ring-bound companion twice.
 */
public class Card4_309 extends AbstractEvent {
    public Card4_309() {
        super(Side.FREE_PEOPLE, 0, Culture.SHIRE, "Light Shining Faintly", Phase.FELLOWSHIP);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new AddBurdenEffect(self.getOwner(), self, 1));
        action.appendEffect(
                new ChooseAndHealCharactersEffect(action, playerId, 1, 1, 2, CardType.COMPANION, Keyword.RING_BOUND));
        return action;
    }
}
