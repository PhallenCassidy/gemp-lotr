package com.gempukku.lotro.cards.set15.orc;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.AddUntilEndOfTurnModifierEffect;
import com.gempukku.lotro.logic.effects.RemoveTwilightEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDiscardEffect;
import com.gempukku.lotro.logic.effects.discount.OptionalDiscardDiscountEffect;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.UnrespondableEffect;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Hunters
 * Side: Shadow
 * Culture: Orc
 * Twilight Cost: 10
 * Type: Minion • Troll
 * Strength: 22
 * Vitality: 6
 * Site: 5
 * Game Text: When you play this minion, you may discard 5 [ORC] minions from play to make it twilight cost -10
 * and fierce. Shadow: Remove (3) to play an [ORC] Orc from your discard pile. Its twilight cost is -2.
 */
public class Card15_112 extends AbstractMinion {
    public Card15_112() {
        super(10, 22, 6, 5, Race.TROLL, Culture.ORC, "Mountain-troll");
    }

    @Override
    public int getPotentialDiscount(LotroGame game, String playerId, PhysicalCard self) {
        if (PlayConditions.canDiscardFromPlay(self, game, 5, Culture.ORC, CardType.MINION))
            return 10;
        return 0;
    }

    @Override
    public void appendPotentialDiscountEffects(LotroGame game, final CostToEffectAction action, String playerId, final PhysicalCard self) {
        action.appendPotentialDiscount(new OptionalDiscardDiscountEffect(action, 10, playerId, 5, Culture.ORC, CardType.MINION) {
            @Override
            protected void discountPaidCallback() {
                action.appendEffect(
                        new UnrespondableEffect() {
                            @Override
                            protected void doPlayEffect(LotroGame game) {
                                action.appendEffect(
                                        new AddUntilEndOfTurnModifierEffect(
                                                new KeywordModifier(self, self, Keyword.FIERCE)));
                            }
                        });
            }
        });
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SHADOW, self, 3)
                && PlayConditions.canPlayFromDiscard(playerId, game, 3, -2, Culture.ORC, Race.ORC)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new RemoveTwilightEffect(3));
            action.appendEffect(
                    new ChooseAndPlayCardFromDiscardEffect(playerId, game, -2, Culture.ORC, Race.ORC));
            return Collections.singletonList(action);
        }
        return null;
    }
}
