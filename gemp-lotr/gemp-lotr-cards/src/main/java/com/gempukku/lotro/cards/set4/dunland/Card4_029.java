package com.gempukku.lotro.cards.set4.dunland;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.modifiers.Condition;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.TwilightCostModifier;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Dunland
 * Twilight Cost: 0
 * Type: Condition
 * Game Text: Plays to your support area. While a [DUNLAND] Man is stacked on a site, the Shadow number of each site
 * is +5.
 */
public class Card4_029 extends AbstractPermanent {
    public Card4_029() {
        super(Side.SHADOW, 0, CardType.CONDITION, Culture.DUNLAND, "No Refuge", null, true);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new TwilightCostModifier(self, CardType.SITE,
                        new Condition() {
                            @Override
                            public boolean isFullfilled(LotroGame game) {
                                return Filters.countActive(game, CardType.SITE,
                                        Filters.hasStacked(Filters.and(Culture.DUNLAND, Race.MAN))) > 0;
                            }
                        }, 5));
    }
}
