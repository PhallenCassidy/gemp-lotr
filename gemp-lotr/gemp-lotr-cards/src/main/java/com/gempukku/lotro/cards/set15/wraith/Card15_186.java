package com.gempukku.lotro.cards.set15.wraith;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.logic.modifiers.CantTakeWoundsModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.SpotCondition;
import com.gempukku.lotro.logic.modifiers.condition.AndCondition;
import com.gempukku.lotro.logic.modifiers.condition.NotCondition;
import com.gempukku.lotro.logic.modifiers.condition.PhaseCondition;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Hunters
 * Side: Shadow
 * Culture: Wraith
 * Twilight Cost: 5
 * Type: Minion • Nazgul
 * Strength: 10
 * Vitality: 3
 * Site: 3
 * Game Text: Fierce. While you can spot 6 companions, [WRAITH] minions cannot take wounds
 * (except during skirmish phases).
 * Skirmish: If this minion is mounted, exert him twice to wound a companion bearing a [WRAITH] condition.
 */
public class Card15_186 extends AbstractMinion {
    public Card15_186() {
        super(5, 10, 3, 3, Race.NAZGUL, Culture.WRAITH, Names.nelya, "Fell Rider", true);
        addKeyword(Keyword.FIERCE);
    }

    @Override
    public java.util.List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return java.util.Collections.singletonList(new CantTakeWoundsModifier(self,
new AndCondition(
new NotCondition(new PhaseCondition(Phase.SKIRMISH)),
new SpotCondition(6, CardType.COMPANION)), Filters.and(Culture.WRAITH, CardType.MINION)));
}

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SKIRMISH, self, 0)
                && PlayConditions.canSpot(game, self, Filters.mounted)
                && PlayConditions.canSelfExert(self, 2, game)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendEffect(
                    new ChooseAndWoundCharactersEffect(action, playerId, 1, 1, CardType.COMPANION, Filters.hasAttached(Culture.WRAITH, CardType.CONDITION)));
            return Collections.singletonList(action);
        }
        return null;
    }
}
