package com.gempukku.lotro.cards.set2.site;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.TwilightCostModifier;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Mines of Moria
 * Twilight Cost: 4
 * Type: Site
 * Site: 4
 * Game Text: Underground. The twilight cost of the first [MORIA] archer played each Shadow phase is -2.
 */
public class Card2_118 extends AbstractSite {
    public Card2_118() {
        super("Great Chasm", SitesBlock.FELLOWSHIP, 4, 4, Direction.RIGHT);
        addKeyword(Keyword.UNDERGROUND);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, final PhysicalCard self) {
        return Collections.singletonList(
                new TwilightCostModifier(self,
                        Filters.and(
                                Culture.MORIA,
                                Keyword.ARCHER,
                                new Filter() {
                                    @Override
                                    public boolean accepts(LotroGame game, PhysicalCard physicalCard) {
                                        return game.getGameState().getCurrentPhase() == Phase.SHADOW && game.getModifiersQuerying().getUntilEndOfPhaseLimitCounter(self, Phase.SHADOW).getUsedLimit() < 1;
                                    }
                                }
                        ), -2));
    }

    @Override
    public List<RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.played(game, effectResult, Filters.and(Culture.MORIA, Keyword.ARCHER))
                && PlayConditions.isPhase(game, Phase.SHADOW))
            game.getModifiersQuerying().getUntilEndOfPhaseLimitCounter(self, Phase.SHADOW).incrementToLimit(1, 1);
        return null;
    }
}
