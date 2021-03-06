package com.gempukku.lotro.cards.set10.elven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.effects.ForEachYouSpotEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Mount Doom
 * Side: Free
 * Culture: Elven
 * Twilight Cost: 2
 * Type: Companion • Elf
 * Strength: 6
 * Vitality: 3
 * Resistance: 6
 * Game Text: Skirmish: Exert Aegnor to make a minion skirmishing an unbound [ELVEN] companion strength -1 for each
 * archer you spot.
 */
public class Card10_004 extends AbstractCompanion {
    public Card10_004() {
        super(2, 6, 3, 6, Culture.ELVEN, Race.ELF, null, "Aegnor", "Elven Escort", true);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(final String playerId, final LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.SKIRMISH, self)
                && PlayConditions.canSelfExert(self, game)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendEffect(
                    new ForEachYouSpotEffect(playerId, Keyword.ARCHER) {
                        @Override
                        protected void spottedCards(int spotCount) {
                            action.insertEffect(
                                    new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, -spotCount, CardType.MINION, Filters.inSkirmishAgainst(Culture.ELVEN, Filters.unboundCompanion)));
                        }
                    });
            return Collections.singletonList(action);
        }

        return null;
    }
}
