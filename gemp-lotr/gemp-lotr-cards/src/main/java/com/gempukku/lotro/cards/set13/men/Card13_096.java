package com.gempukku.lotro.cards.set13.men;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.SpotCondition;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;
import com.gempukku.lotro.logic.modifiers.evaluator.CountActiveEvaluator;
import com.gempukku.lotro.logic.modifiers.evaluator.MultiplyEvaluator;

import java.util.Collections;
import java.util.List;

/**
 * Set: Bloodlines
 * Side: Shadow
 * Culture: Men
 * Twilight Cost: 3
 * Type: Minion • Man
 * Strength: 8
 * Vitality: 2
 * Site: 4
 * Game Text: While you can spot another [MEN] minion, this minion is strength +2 for each companion assigned to
 * a skirmish.
 */
public class Card13_096 extends AbstractMinion {
    public Card13_096() {
        super(3, 8, 2, 4, Race.MAN, Culture.MEN, "Merciless Dunlending");
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return Collections.singletonList(new StrengthModifier(self, self,
new SpotCondition(Filters.not(self), Culture.MEN, CardType.MINION),
new MultiplyEvaluator(2, new CountActiveEvaluator(CardType.COMPANION, Filters.assignedToSkirmish))));
}
}
