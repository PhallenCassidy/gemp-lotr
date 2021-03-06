package com.gempukku.lotro.cards.set17.uruk_hai;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.modifiers.CantTakeWoundsModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.SpotCondition;
import com.gempukku.lotro.logic.modifiers.condition.AndCondition;
import com.gempukku.lotro.logic.modifiers.condition.NotCondition;
import com.gempukku.lotro.logic.modifiers.condition.PhaseCondition;

/**
 * Set: Rise of Saruman
 * Side: Shadow
 * Culture: Uruk-hai
 * Twilight Cost: 5
 * Type: Minion • Uruk-Hai
 * Strength: 13
 * Vitality: 1
 * Site: 5
 * Game Text: Damage +1. While you control a site, this minion cannot take wounds, except during the skirmish phase.
 */
public class Card17_119 extends AbstractMinion {
    public Card17_119() {
        super(5, 13, 1, 5, Race.URUK_HAI, Culture.URUK_HAI, "White Hand Aggressor");
        addKeyword(Keyword.DAMAGE, 1);
    }

    @Override
    public java.util.List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return java.util.Collections.singletonList(new CantTakeWoundsModifier(self,
new AndCondition(
new SpotCondition(Filters.siteControlled(self.getOwner())),
new NotCondition(new PhaseCondition(Phase.SKIRMISH))),
self));
}
}
