package com.gempukku.lotro.cards.build.field.effect.requirement;

import com.gempukku.lotro.cards.build.CardGenerationEnvironment;
import com.gempukku.lotro.cards.build.FilterableSource;
import com.gempukku.lotro.cards.build.InvalidCardDefinitionException;
import com.gempukku.lotro.cards.build.Requirement;
import com.gempukku.lotro.cards.build.field.FieldUtils;
import com.gempukku.lotro.common.Filterable;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import org.json.simple.JSONObject;

import java.util.Collection;

public class MemoryIs implements RequirementProducer {
    @Override
    public Requirement getPlayRequirement(JSONObject object, CardGenerationEnvironment environment) throws InvalidCardDefinitionException {
        FieldUtils.validateAllowedFields(object, "memory", "value");

        final String memory = FieldUtils.getString(object.get("memory"), "memory");
        final String value = FieldUtils.getString(object.get("value"), "value");

        return (actionContext) -> {
            String valueFromMemory = actionContext.getValueFromMemory(memory);
            return valueFromMemory != null && valueFromMemory.equals(value);
        };
    }
}
