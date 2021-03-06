package com.gempukku.lotro.cards.set18.gollum;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.common.Token;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.DrawCardsEffect;
import com.gempukku.lotro.logic.effects.OptionalEffect;
import com.gempukku.lotro.logic.effects.ReinforceTokenEffect;

/**
 * Set: Treachery & Deceit
 * Side: Free
 * Culture: Gollum
 * Twilight Cost: 0
 * Type: Event • Skirmish
 * Game Text: Reinforce a [GOLLUM] token. Each Shadow player may draw a card.
 */
public class Card18_037 extends AbstractEvent {
    public Card18_037() {
        super(Side.FREE_PEOPLE, 0, Culture.GOLLUM, "Trusted Promise", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ReinforceTokenEffect(self, playerId, Token.GOLLUM));
        for (String shadowPlayer : GameUtils.getShadowPlayers(game)) {
            action.appendEffect(
                    new OptionalEffect(action, shadowPlayer,
                            new DrawCardsEffect(action, shadowPlayer, 1)));

        }
        return action;
    }
}
