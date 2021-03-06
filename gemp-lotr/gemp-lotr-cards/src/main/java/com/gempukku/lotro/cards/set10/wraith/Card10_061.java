package com.gempukku.lotro.cards.set10.wraith;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.modifiers.evaluator.CardMatchesEvaluator;

/**
 * Set: Mount Doom
 * Side: Shadow
 * Culture: Wraith
 * Twilight Cost: 1
 * Type: Event • Skirmish
 * Game Text: Make a [WRAITH] minion strength +2 (or +4 if skirmishing a companion of the same culture as a card in the
 * dead pile).
 */
public class Card10_061 extends AbstractEvent {
    public Card10_061() {
        super(Side.SHADOW, 1, Culture.WRAITH, "Houses of Lamentation", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId,
                        new CardMatchesEvaluator(2, 4, Filters.inSkirmishAgainst(CardType.COMPANION,
                                new Filter() {
                                    @Override
                                    public boolean accepts(LotroGame game, PhysicalCard physicalCard) {
                                        final Culture companionCulture = physicalCard.getBlueprint().getCulture();
                                        return Filters.filter(game.getGameState().getDeadPile(game.getGameState().getCurrentPlayerId()), game, companionCulture).size() > 0;
                                    }
                                })), Culture.WRAITH, CardType.MINION));
        return action;
    }
}
