package com.gempukku.lotro.cards.set3.isengard;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.modifiers.CancelKeywordBonusTargetModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.List;

/**
 * Set: Realms of Elf-lords
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 1
 * Type: Condition
 * Game Text: Plays to your support area. Each character skirmishing an [ISENGARD] Orc loses all damage bonuses
 * from weapons.
 */
public class Card3_072 extends AbstractPermanent {
    public Card3_072() {
        super(Side.SHADOW, 1, CardType.CONDITION, Culture.ISENGARD, "Trapped and Alone");
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new CancelKeywordBonusTargetModifier(self, Keyword.DAMAGE,
                        Filters.and(Filters.character, Filters.inSkirmishAgainst(Culture.ISENGARD, Race.ORC)),
                        Filters.weapon));
    }
}
