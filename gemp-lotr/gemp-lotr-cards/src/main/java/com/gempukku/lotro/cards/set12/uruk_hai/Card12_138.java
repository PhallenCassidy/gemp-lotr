package com.gempukku.lotro.cards.set12.uruk_hai;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Black Rider
 * Side: Shadow
 * Culture: Uruk-hai
 * Twilight Cost: 3
 * Type: Event • Maneuver
 * Game Text: Toil 2. (For each [URUK-HAI] character you exert when playing this, its twilight cost is -2) Spot an
 * [URUK-HAI] minion to discard a possession borne by a companion who has resistance 4 or less.
 */
public class Card12_138 extends AbstractEvent {
    public Card12_138() {
        super(Side.SHADOW, 3, Culture.URUK_HAI, "Broken Heirloom", Phase.MANEUVER);
        addKeyword(Keyword.TOIL, 2);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Culture.URUK_HAI, CardType.MINION);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, CardType.POSSESSION, Filters.attachedTo(CardType.COMPANION, Filters.maxResistance(4))));
        return action;
    }
}
