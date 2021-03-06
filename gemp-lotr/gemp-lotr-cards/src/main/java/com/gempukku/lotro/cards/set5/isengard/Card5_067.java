package com.gempukku.lotro.cards.set5.isengard;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.List;

/**
 * Set: Battle of Helm's Deep
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 1
 * Type: Minion • Orc
 * Strength: 5
 * Vitality: 2
 * Site: 4
 * Game Text: Warg-rider. While this minion is not exhausted, he is fierce.
 */
public class Card5_067 extends AbstractMinion {
    public Card5_067() {
        super(1, 5, 2, 4, Race.ORC, Culture.ISENGARD, "Warg-rider");
        addKeyword(Keyword.WARG_RIDER);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new KeywordModifier(self, Filters.and(self, Filters.not(Filters.exhausted)), Keyword.FIERCE));
    }
}
