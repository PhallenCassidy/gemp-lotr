package com.gempukku.lotro.cards.set11.shire;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;
import com.gempukku.lotro.logic.modifiers.condition.LocationCondition;

import java.util.Collections;
import java.util.List;

/**
 * Set: Shadows
 * Side: Free
 * Culture: Shire
 * Twilight Cost: 1
 * Type: Companion • Hobbit
 * Strength: 3
 * Vitality: 3
 * Resistance: 8
 * Game Text: While Farmer Maggot is at a dwelling or forest site, he is strength +4.
 */
public class Card11_163 extends AbstractCompanion {
    public Card11_163() {
        super(1, 3, 3, 8, Culture.SHIRE, Race.HOBBIT, null, "Farmer Maggot", "Hobbit of the Marish", true);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return Collections.singletonList(new StrengthModifier(self, self, new LocationCondition(Filters.or(Keyword.DWELLING, Keyword.FOREST)), 4));
}
}
