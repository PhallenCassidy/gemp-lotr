package com.gempukku.lotro.cards.set15.uruk_hai;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;
import com.gempukku.lotro.logic.modifiers.evaluator.CountActiveEvaluator;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Hunters
 * Side: Shadow
 * Culture: Uruk-hai
 * Twilight Cost: 6
 * Type: Minion • Uruk-Hai
 * Strength: 14
 * Vitality: 3
 * Site: 5
 * Game Text: Damage +1. This minion is strength +1 for each site you control.
 */
public class Card15_155 extends AbstractMinion {
    public Card15_155() {
        super(6, 14, 3, 5, Race.URUK_HAI, Culture.URUK_HAI, "Advancing Horde");
        addKeyword(Keyword.DAMAGE, 1);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return Collections.singletonList(new StrengthModifier(self, self, null, new CountActiveEvaluator(Filters.siteControlled(self.getOwner()))));
}
}
