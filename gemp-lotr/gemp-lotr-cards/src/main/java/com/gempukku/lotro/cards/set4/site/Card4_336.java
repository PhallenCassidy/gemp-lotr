package com.gempukku.lotro.cards.set4.site;

import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.effects.AddTwilightEffect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Twilight Cost: 3
 * Type: Site
 * Site: 2T
 * Game Text: Plains. Each time the Free Peoples player assigns a companion or ally to a skirmish, add (1).
 */
public class Card4_336 extends AbstractSite {
    public Card4_336() {
        super("Wold of Rohan", SitesBlock.TWO_TOWERS, 2, 3, Direction.LEFT);
        addKeyword(Keyword.PLAINS);
    }

    @Override
    public List<RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.assignedToSkirmish(game, effectResult, Side.FREE_PEOPLE, Filters.or(CardType.COMPANION, CardType.ALLY))) {
            RequiredTriggerAction action = new RequiredTriggerAction(self);
            action.appendEffect(
                    new AddTwilightEffect(self, 1));
            return Collections.singletonList(action);
        }
        return null;
    }
}
