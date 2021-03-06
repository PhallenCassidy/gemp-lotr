package com.gempukku.lotro.cards.set4.gondor;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gondor
 * Twilight Cost: 3
 * Type: Companion • Man
 * Strength: 6
 * Vitality: 3
 * Resistance: 6
 * Game Text: Ring-bound. Ranger. When this companion is in your starting fellowship, his twilight cost is -1.
 * While skirmishing a roaming minion, this companion is strength +2.
 */
public class Card4_130 extends AbstractCompanion {
    public Card4_130() {
        super(3, 6, 3, 6, Culture.GONDOR, Race.MAN, null, "Ranger of Ithilien");
        addKeyword(Keyword.RING_BOUND);
        addKeyword(Keyword.RANGER);
    }

    @Override
    public int getTwilightCostModifier(LotroGame game, PhysicalCard self, PhysicalCard target) {
        if (game.getGameState().getCurrentPhase() == Phase.PLAY_STARTING_FELLOWSHIP)
            return -1;
        return 0;
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new StrengthModifier(self,
                        Filters.and(
                                self,
                                Filters.inSkirmishAgainst(
                                        Filters.and(
                                                CardType.MINION,
                                                Keyword.ROAMING))), 2));
    }
}
