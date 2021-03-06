package com.gempukku.lotro.cards.set4.gondor;

import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.effects.ForEachYouSpotEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gondor
 * Twilight Cost: 4
 * Type: Companion • Man
 * Strength: 8
 * Vitality: 4
 * Resistance: 6
 * Signet: Aragorn
 * Game Text: Ranger. Each time the fellowship moves, you may wound a minion for each unbound Hobbit you spot.
 */
public class Card4_364 extends AbstractCompanion {
    public Card4_364() {
        super(4, 8, 4, 6, Culture.GONDOR, Race.MAN, Signet.ARAGORN, "Aragorn", "Wingfoot", true);
        addKeyword(Keyword.RANGER);
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(final String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.moves(game, effectResult)
                && PlayConditions.canSpot(game, Race.HOBBIT, Filters.unboundCompanion)) {
            final OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendEffect(
                    new ForEachYouSpotEffect(playerId, Race.HOBBIT, Filters.unboundCompanion) {
                        @Override
                        protected void spottedCards(int spotCount) {
                            for (int i = 0; i < spotCount; i++)
                                action.appendEffect(
                                        new ChooseAndWoundCharactersEffect(action, playerId, 1, 1, CardType.MINION));
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
