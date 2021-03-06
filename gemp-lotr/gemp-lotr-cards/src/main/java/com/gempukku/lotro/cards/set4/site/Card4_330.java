package com.gempukku.lotro.cards.set4.site;

import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.modifiers.MoveLimitModifier;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.List;

/**
 * Set: The Two Towers
 * Twilight Cost: 2
 * Type: Site
 * Site: 2T
 * Game Text: Forest. While you can spot an unbound Hobbit at Derndingle, the move limit is +1 for this turn.
 */
public class Card4_330 extends AbstractSite {
    public Card4_330() {
        super("Derndingle", SitesBlock.TWO_TOWERS, 2, 2, Direction.LEFT);
        addKeyword(Keyword.FOREST);
    }

    @Override
    public List<RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (game.getModifiersQuerying().getUntilEndOfTurnLimitCounter(self).getUsedLimit() < 1
                && Filters.canSpot(game, Race.HOBBIT, Filters.unboundCompanion)) {
            game.getModifiersEnvironment().addUntilEndOfTurnModifier(
                    new MoveLimitModifier(self, 1));
            game.getModifiersQuerying().getUntilEndOfTurnLimitCounter(self).incrementToLimit(1, 1);
        }
        return null;
    }
}
