package com.gempukku.lotro.cards.set11.gollum;

import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.effects.AddBurdenEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: Shadows
 * Side: Shadow
 * Culture: Gollum
 * Twilight Cost: 0
 * Type: Condition • Support Area
 * Game Text: Each time a companion loses a fierce skirmish, you may exert Gollum to add a burden.
 */
public class Card11_048 extends AbstractPermanent {
    public Card11_048() {
        super(Side.SHADOW, 0, CardType.CONDITION, Culture.GOLLUM, "Not Yet Vanquished", null, true);
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.losesSkirmish(game, effectResult, CardType.COMPANION)
                && game.getGameState().isFierceSkirmishes()
                && PlayConditions.canExert(self, game, Filters.gollum)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendCost(
                    new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Filters.gollum));
            action.appendEffect(
                    new AddBurdenEffect(playerId, self, 1));
            return Collections.singletonList(action);
        }
        return null;
    }
}
