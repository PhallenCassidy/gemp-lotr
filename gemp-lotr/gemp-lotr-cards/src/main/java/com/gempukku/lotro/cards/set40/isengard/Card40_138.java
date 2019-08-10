package com.gempukku.lotro.cards.set40.isengard;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddThreatsEffect;
import com.gempukku.lotro.logic.effects.ChoiceEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDeckEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDiscardEffect;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: Seeking My Counsel
 * Set: Second Edition
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 2
 * Type: Event - Shadow
 * Card Number: 1R138
 * Game Text: Play Saruman from your draw deck or discard pile to add X threats, where X is the fellowship's current region number.
 */
public class Card40_138 extends AbstractEvent {
    public Card40_138() {
        super(Side.SHADOW, 2, Culture.ISENGARD, "Seeking My Counsel", Phase.SHADOW);
    }

    @Override
    public boolean checkPlayRequirements(String playerId, LotroGame game, PhysicalCard self, int withTwilightRemoved, int twilightModifier, boolean ignoreRoamingPenalty, boolean ignoreCheckingDeadPile) {
        return super.checkPlayRequirements(playerId, game, self, withTwilightRemoved, twilightModifier, ignoreRoamingPenalty, ignoreCheckingDeadPile)
                && (PlayConditions.canPlayFromDeck(playerId, game, Filters.saruman)
                || PlayConditions.canPlayFromDiscard(playerId, game, Filters.saruman));
    }

    @Override
    public PlayEventAction getPlayCardAction(String playerId, LotroGame game, PhysicalCard self, int twilightModifier, boolean ignoreRoamingPenalty) {
        PlayEventAction action = new PlayEventAction(self);
        List<Effect> possibleCosts = new ArrayList<Effect>(2);
        possibleCosts.add(
                new ChooseAndPlayCardFromDeckEffect(playerId, Filters.saruman));
        possibleCosts.add(
                new ChooseAndPlayCardFromDiscardEffect(playerId, game, Filters.saruman));
        action.appendCost(
                new ChoiceEffect(
                        action, playerId, possibleCosts));
        action.appendEffect(
                new AddThreatsEffect(playerId, self,
                        new Evaluator() {
                            @Override
                            public int evaluateExpression(LotroGame game, PhysicalCard cardAffected) {
                                return GameUtils.getRegion(game);
                            }
                        }));
        return action;
    }
}