package se.alipsa.groovy.stats.regression

import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrix.Stat

import java.math.RoundingMode

/**
 * Linear regression, also known as Simple Linear Regression is a regression algorithm that models the
 * relationship between a dependent variable and one independent variable.
 * A linear regression model shows a relationship that is linear or a sloped straight line.
 *
 * In Linear Regression, the dependent variable must be a real or continuous value.
 * However, you can measure the independent variable on continuous or categorical values.
 *
 * Simple Linear Regression can be expressed using the following formula:
 * Y = a + bX
 * Where:
 * <ul>
 *   <li>Y is the dependent variable.</li>
 *   <li>a is the intercept of the Regression line.</li>
 *   <li>b is the slope of the line.</li>
 *   <li>X is the independent variable.</li>
 * </ul>
 */
class LinearRegression {

  BigDecimal slope
  BigDecimal intercept

  LinearRegression(Matrix table, String x, String y) {
    this(table[x] as List<? extends Number>, table[y] as List<? extends Number>)
  }

  LinearRegression(List<? extends Number> x, List<? extends Number> y) {
    if (x.size() != y.size()) {
      throw new IllegalArgumentException("Must have equal number of X and Y data points for a linear regression")
    }

    Integer numberOfDataValues = x.size()

    List<? extends Number> xSquared = x.collect {it * it }

    List<BigDecimal> xMultipliedByY = (0 ..< numberOfDataValues).collect {
      i -> x.get(i) * y.get(i)
    }

    BigDecimal xSummed = Stat.sum(x)
    BigDecimal ySummed = Stat.sum(y)
    BigDecimal sumOfXSquared = Stat.sum(xSquared)
    BigDecimal sumOfXMultipliedByY = Stat.sum(xMultipliedByY)

    BigDecimal slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed
    BigDecimal slopeDenominator = numberOfDataValues * sumOfXSquared - xSummed ** 2
    slope = slopeNominator / slopeDenominator

    BigDecimal interceptNominator = ySummed - slope * xSummed
    BigDecimal interceptDenominator = numberOfDataValues
    intercept = interceptNominator / interceptDenominator
  }

  BigDecimal predict(Number dependentVariable) {
    return (slope * dependentVariable) + intercept
  }

  BigDecimal predict(Number dependentVariable, int numberOfDecimals) {
    return predict(dependentVariable).setScale(numberOfDecimals, RoundingMode.HALF_EVEN)
  }

  List<BigDecimal> predict(List<Number> dependentVariables) {
    List<BigDecimal> predictions = []
    dependentVariables.each {
      predictions << predict(it)
    }
    return predictions
  }

  List<BigDecimal> predict(List<Number> dependentVariables, int numberOfDecimals) {
    List<BigDecimal> predictions = []
    dependentVariables.each {
      predictions << predict(it, numberOfDecimals)
    }
    return predictions
  }

  BigDecimal getSlope() {
    return slope
  }

  BigDecimal getSlope(int numberOfDecimals) {
    return slope.setScale(numberOfDecimals, RoundingMode.HALF_EVEN)
  }

  BigDecimal getIntercept() {
    return intercept
  }

  BigDecimal getIntercept(int numberOfDecimals) {
    return intercept.setScale(numberOfDecimals, RoundingMode.HALF_EVEN)
  }

  @Override
  String toString() {
    return "Y = ${getSlope(2)}X ${intercept > 0 ? '+' : '-'} ${getIntercept(2).abs()}"
  }
}
