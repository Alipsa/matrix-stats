package se.alipsa.groovy.stats.regression

import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrix.Stat

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
      throw new IllegalArgumentException("Must have equal number of X and Y data points")
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

  List<BigDecimal> predict(List<Number> dependentVariables) {
    List<BigDecimal> predictions = []
    dependentVariables.each {
      predictions << predict(it)
    }
    return predictions
  }

  BigDecimal getSlope() {
    return slope
  }

  BigDecimal getIntercept() {
    return intercept
  }
}
