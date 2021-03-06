package com.gempukku.lotro.cards.set15.dwarven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.effects.ShuffleCardsFromPlayIntoDeckEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;

/**
 * Set: The Hunters
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 2
 * Type: Event • Maneuver
 * Game Text: Exert a Dwarf to shuffle a [DWARVEN] condition with one or more [DWARVEN] tokens on it into your
 * draw deck. Wound a minion for each [DWARVEN] token that was on that condition.
 */
public class Card15_004 extends AbstractEvent {
    public Card15_004() {
        super(Side.FREE_PEOPLE, 2, Culture.DWARVEN, "The Fortunes of Balin's Folk", Phase.MANEUVER);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canExert(self, game, Race.DWARF);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Race.DWARF));
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose a DWARVEN condition", Culture.DWARVEN, CardType.CONDITION, Filters.hasToken(Token.DWARVEN)) {
                    @Override
                    protected void cardSelected(LotroGame game, PhysicalCard card) {
                        int tokens = game.getGameState().getTokenCount(card, Token.DWARVEN);
                        action.appendEffect(
                                new ShuffleCardsFromPlayIntoDeckEffect(self, playerId, Collections.singleton(card)));
                        for (int i = 0; i < tokens; i++)
                            action.appendEffect(
                                    new ChooseAndWoundCharactersEffect(action, playerId, 1, 1, CardType.MINION));
                    }
                });
        return action;
    }
}
