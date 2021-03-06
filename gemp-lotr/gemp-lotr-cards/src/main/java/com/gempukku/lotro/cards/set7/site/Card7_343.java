package com.gempukku.lotro.cards.set7.site;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.RemoveBurdenEffect;
import com.gempukku.lotro.logic.modifiers.ArcheryTotalModifier;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Return of the King
 * Twilight Cost: 4
 * Type: Site
 * Site: 4K
 * Game Text: Plains. Archery: Spot your minion and remove 3 burdens to make the minion archery total +3.
 */
public class Card7_343 extends AbstractSite {
    public Card7_343() {
        super("Pelennor Plain", SitesBlock.KING, 4, 4, Direction.RIGHT);
        addKeyword(Keyword.PLAINS);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseSiteDuringPhase(game, Phase.ARCHERY, self)
                && PlayConditions.canSpot(game, Filters.owner(playerId), CardType.MINION)
                && PlayConditions.canRemoveBurdens(game, self, 3)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new RemoveBurdenEffect(playerId, self, 3));
            action.appendEffect(
                    new AddUntilEndOfPhaseModifierEffect(
                            new ArcheryTotalModifier(self, Side.SHADOW, 3)));
            return Collections.singletonList(action);
        }
        return null;
    }
}
