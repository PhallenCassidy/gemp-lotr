package com.gempukku.lotro.cards.set2.shire;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.PreventableCardEffect;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.effects.WoundCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPreventCardEffect;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: Mines of Moria
 * Side: Free
 * Culture: Shire
 * Twilight Cost: 1
 * Type: Condition
 * Game Text: Stealth. Plays to your support area. Response: If a Hobbit is about to take a wound, discard this
 * condition to prevent that wound.
 */
public class Card2_106 extends AbstractPermanent {
    public Card2_106() {
        super(Side.FREE_PEOPLE, 1, CardType.CONDITION, Culture.SHIRE, "Nice Imitation");
        addKeyword(Keyword.STEALTH);
    }

    @Override
    public List<? extends ActivateCardAction> getOptionalInPlayBeforeActions(String playerId, LotroGame game, Effect effect, PhysicalCard self) {
        if (TriggerConditions.isGettingWounded(effect, game, Race.HOBBIT)) {
            final WoundCharactersEffect woundEffect = (WoundCharactersEffect) effect;
            Collection<PhysicalCard> woundedCharacters = woundEffect.getAffectedCardsMinusPrevented(game);
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfDiscardEffect(self));
            action.appendEffect(
                    new ChooseAndPreventCardEffect(self, (PreventableCardEffect) effect, playerId, "Choose a Hobbit", Race.HOBBIT));
            return Collections.singletonList(action);
        }
        return null;
    }
}
