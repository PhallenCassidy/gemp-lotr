package com.gempukku.lotro.cards.set8.gandalf;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.Signet;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractCompanion;

/**
 * Set: Siege of Gondor
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 4
 * Type: Companion • Wizard
 * Strength: 7
 * Vitality: 4
 * Resistance: 6
 * Signet: Aragorn
 * Game Text: When Gandalf is in your starting fellowship, his twilight cost is -2.
 */
public class Card8_015 extends AbstractCompanion {
    public Card8_015() {
        super(4, 7, 4, 6, Culture.GANDALF, Race.WIZARD, Signet.ARAGORN, "Gandalf", "Leader of Men", true);
    }

    @Override
    public int getTwilightCostModifier(LotroGame game, PhysicalCard self, PhysicalCard target) {
        if (game.getGameState().getCurrentPhase() == Phase.PLAY_STARTING_FELLOWSHIP)
            return -2;
        return 0;
    }
}
