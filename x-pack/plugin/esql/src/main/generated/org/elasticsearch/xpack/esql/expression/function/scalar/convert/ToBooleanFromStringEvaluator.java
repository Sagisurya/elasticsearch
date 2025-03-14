// Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
// or more contributor license agreements. Licensed under the Elastic License
// 2.0; you may not use this file except in compliance with the Elastic License
// 2.0.
package org.elasticsearch.xpack.esql.expression.function.scalar.convert;

import java.lang.Override;
import java.lang.String;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.compute.data.Block;
import org.elasticsearch.compute.data.BooleanBlock;
import org.elasticsearch.compute.data.BytesRefBlock;
import org.elasticsearch.compute.data.BytesRefVector;
import org.elasticsearch.compute.data.Vector;
import org.elasticsearch.compute.operator.DriverContext;
import org.elasticsearch.compute.operator.EvalOperator;
import org.elasticsearch.xpack.esql.core.tree.Source;

/**
 * {@link EvalOperator.ExpressionEvaluator} implementation for {@link ToBoolean}.
 * This class is generated. Edit {@code ConvertEvaluatorImplementer} instead.
 */
public final class ToBooleanFromStringEvaluator extends AbstractConvertFunction.AbstractEvaluator {
  public ToBooleanFromStringEvaluator(EvalOperator.ExpressionEvaluator field, Source source,
      DriverContext driverContext) {
    super(driverContext, field, source);
  }

  @Override
  public String name() {
    return "ToBooleanFromString";
  }

  @Override
  public Block evalVector(Vector v) {
    BytesRefVector vector = (BytesRefVector) v;
    int positionCount = v.getPositionCount();
    BytesRef scratchPad = new BytesRef();
    if (vector.isConstant()) {
      return driverContext.blockFactory().newConstantBooleanBlockWith(evalValue(vector, 0, scratchPad), positionCount);
    }
    try (BooleanBlock.Builder builder = driverContext.blockFactory().newBooleanBlockBuilder(positionCount)) {
      for (int p = 0; p < positionCount; p++) {
        builder.appendBoolean(evalValue(vector, p, scratchPad));
      }
      return builder.build();
    }
  }

  private static boolean evalValue(BytesRefVector container, int index, BytesRef scratchPad) {
    BytesRef value = container.getBytesRef(index, scratchPad);
    return ToBoolean.fromKeyword(value);
  }

  @Override
  public Block evalBlock(Block b) {
    BytesRefBlock block = (BytesRefBlock) b;
    int positionCount = block.getPositionCount();
    try (BooleanBlock.Builder builder = driverContext.blockFactory().newBooleanBlockBuilder(positionCount)) {
      BytesRef scratchPad = new BytesRef();
      for (int p = 0; p < positionCount; p++) {
        int valueCount = block.getValueCount(p);
        int start = block.getFirstValueIndex(p);
        int end = start + valueCount;
        boolean positionOpened = false;
        boolean valuesAppended = false;
        for (int i = start; i < end; i++) {
          boolean value = evalValue(block, i, scratchPad);
          if (positionOpened == false && valueCount > 1) {
            builder.beginPositionEntry();
            positionOpened = true;
          }
          builder.appendBoolean(value);
          valuesAppended = true;
        }
        if (valuesAppended == false) {
          builder.appendNull();
        } else if (positionOpened) {
          builder.endPositionEntry();
        }
      }
      return builder.build();
    }
  }

  private static boolean evalValue(BytesRefBlock container, int index, BytesRef scratchPad) {
    BytesRef value = container.getBytesRef(index, scratchPad);
    return ToBoolean.fromKeyword(value);
  }

  public static class Factory implements EvalOperator.ExpressionEvaluator.Factory {
    private final Source source;

    private final EvalOperator.ExpressionEvaluator.Factory field;

    public Factory(EvalOperator.ExpressionEvaluator.Factory field, Source source) {
      this.field = field;
      this.source = source;
    }

    @Override
    public ToBooleanFromStringEvaluator get(DriverContext context) {
      return new ToBooleanFromStringEvaluator(field.get(context), source, context);
    }

    @Override
    public String toString() {
      return "ToBooleanFromStringEvaluator[field=" + field + "]";
    }
  }
}
