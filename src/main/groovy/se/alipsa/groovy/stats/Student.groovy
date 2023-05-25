package se.alipsa.groovy.stats

import se.alipsa.groovy.matrix.Stat

import java.math.RoundingMode

/**
 * A t-test is any statistical hypothesis test in which the test statistic follows a Student's t-distribution
 * under the null hypothesis. It is most commonly applied when the test statistic would follow a normal
 * distribution if the value of a scaling term in the test statistic were known (typically,
 * the scaling term is unknown and therefore a nuisance parameter). When the scaling term is estimated based
 * on the data, the test statistic—under certain conditions—follows a Student's t distribution.
 * The t-test's most common application is to test whether the means of two populations are different.
 *
 * The formula is:
 * t = ( x̄<sub>1</sub> - x̄<sub>2</sub> ) /
 * ( sqrt(S<sub>1</sub><sup>2</sup> / N<sub>1</sub> * S<sub>2</sub><sup>2</sup> / N<sub>2</sub>)
 * where
 * <ul>
 *   <li>x̄<sub>1</sub> is the mean of first data set</li>
 *   <li>x̄<sub>2</sub> is the mean of second data set</li>
 *   <li>S<sub>1</sub><sup>2</sup> is the standard deviation of first data set</li>
 *   <li>S<sub>2</sub><sup>2</sup> is the standard deviation of second data set</li>
 *   <li>N<sub>1</sub> is the number of elements in the first data set</li>
 *   <li>N<sub>2</sub> is the number of elements in the second data set</li>
 * </ul>
 * @return
 */
class Student {

  static Result twoSample(List<? extends Number> first, List<? extends Number> second) {
    BigDecimal mean1 = Stat.mean(first)
    BigDecimal mean2 = Stat.mean(second)
    BigDecimal sd1 = Stat.sd(first)
    BigDecimal sd2 = Stat.sd(second)
    Result result = new Result()
    def t = (mean1 - mean2) / Math.sqrt((sd1 * sd1) / first.size() + (sd2 * sd2) / second.size())
    result.tVal = t
    return result
  }



  static class Result {
    BigDecimal tVal

    BigDecimal getT() {
      return tVal
    }

    BigDecimal getT(int numberOfDecimals) {
      return getT().setScale(numberOfDecimals, RoundingMode.HALF_EVEN)
    }
  }
}
