package com.gempukku.lotro.cards.set4.shire;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.CancelSkirmishEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.modifiers.CantTakeMoreThanXWoundsModifier;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Shire
 * Twilight Cost: 1
 * Type: Event
 * Game Text: Stealth. Skirmish: At sites 1T to 5T, cancel a skirmish involving a Hobbit. At any other site, prevent
 * a Hobbit from taking more than 1 wound.
 */
public class Card4_321 extends AbstractEvent {
    public Card4_321() {
        super(Side.FREE_PEOPLE, 1, Culture.SHIRE, "Swiftly and Softly", Phase.SKIRMISH);
        addKeyword(Keyword.STEALTH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        boolean firstOption = (game.getGameState().getCurrentSiteNumber() <= 5 && game.getGameState().getCurrentSiteBlock() == SitesBlock.TWO_TOWERS);
        if (firstOption) {
            action.appendEffect(
                    new CancelSkirmishEffect(Race.HOBBIT));
        } else {
            action.appendEffect(
                    new ChooseActiveCardEffect(self, playerId, "Choose a Hobbit", Race.HOBBIT) {
                        @Override
                        protected void cardSelected(LotroGame game, final PhysicalCard hobbit) {
                            action.insertEffect(
                                    new AddUntilEndOfPhaseModifierEffect(
                                            new CantTakeMoreThanXWoundsModifier(self, Phase.SKIRMISH, 1, hobbit)));
                        }
                    });
        }
        return action;
    }
}
