package com.gempukku.lotro.cards.set4.raider;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.AddTwilightEffect;
import com.gempukku.lotro.logic.effects.AssignmentEffect;
import com.gempukku.lotro.logic.effects.PreventableEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Raider
 * Twilight Cost: 3
 * Type: Minion • Man
 * Strength: 8
 * Vitality: 2
 * Site: 4
 * Game Text: Southron. Ambush (1). Assignment: Exert this minion and spot 5 companions to assign this minion
 * to the Ringbearer. The Free Peoples player may add (4) to prevent this.
 */
public class Card4_258 extends AbstractMinion {
    public Card4_258() {
        super(3, 8, 2, 4, Race.MAN, Culture.RAIDER, "Southron Wanderer");
        addKeyword(Keyword.SOUTHRON);
        addKeyword(Keyword.AMBUSH, 1);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.ASSIGNMENT, self, 0)
                && PlayConditions.canExert(self, game, self)
                && PlayConditions.canSpot(game, 5, CardType.COMPANION)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendEffect(
                    new PreventableEffect(action,
                            new AssignmentEffect(playerId, Filters.findFirstActive(game, Filters.ringBearer), self),
                            game.getGameState().getCurrentPlayerId(),
                            new PreventableEffect.PreventionCost() {
                                @Override
                                public Effect createPreventionCostForPlayer(CostToEffectAction subAction, String playerId) {
                                    return new AddTwilightEffect(self, 4);
                                }
                            }
                    ));
            return Collections.singletonList(action);
        }
        return null;
    }
}
