# matrix-stats
Statistical functions for Matrix data. E.g correlations, normalizations, and hypothesis tests.


## Correlations
Correlation is a statistical measure that expresses the extent to which two variables are linearly related.
The Correlation class can do the most common types of correlation calculations (Pearson, Spearman, and Kendall).
Pearson correlation is the default one:
```groovy
import static se.alipsa.groovy.stats.Correlation.*
import java.math.RoundingMode

assert 0.95346258924559 == cor([15, 18, 21, 24, 27], [25, 25, 27, 31, 32]).setScale(14, RoundingMode.HALF_EVEN)
```

Add a method parameter to get Spearman and Kendall correlations instead e.g:

```groovy
import static se.alipsa.groovy.stats.Correlation.*
import java.math.RoundingMode
// Spearman
assert 0.45643546458764 == cor([15, 18, 21, 15, 21], [25, 25, 27, 27, 27], SPEARMAN).setScale(14, RoundingMode.HALF_EVEN)

// Kendall
assert -0.47140452079103173 == cor([12, 2, 1, 12, 2], [1, 4, 7, 1, 0], KENDALL)
```
See
[CorrelationTest](https://github.com/Alipsa/matrix-stats/blob/main/src/test/groovy/CorrelationTest.groovy) for some examples.


## Normalizations
Implements various ways of normalizing (scaling) data e.g. 
scale the values so that they have a mean of 0 and a standard deviation of 1 
It implements 4 different approaches
- logarithmic normalization
- Min-Max scaling, Z<sub>i</sub> = ( X<sub>i</sub> - min(X) ) / ( max(X) - min(X) )
- Mean normalization, X´ = ( X - μ ) / ( max(X) - min(X) )
- Standard deviation normalization (Z score), Z = ( X<sub>i</sub> - μ ) / σ

Here is an example:
```groovy
import se.alipsa.groovy.matrix.ListConverter
import se.alipsa.groovy.stats.Normalize
List<BigDecimal> obs = ListConverter.toBigDecimals([1200, 34567, 3456, 12, 3456, 985, 1211])
def expected = [-0.1508444, 0.8147756,  -0.0855572, -0.1852244, -0.0855572, -0.1570664, -0.1505261]
assert expected == Normalize.meanNorm(obs, 7) // 7 is the number of decimals
```

See
[NormalizeTest](https://github.com/Alipsa/matrix-stats/blob/main/src/test/groovy/NormalizeTest.groovy) for some examples.


## LinearRegression
Implements the least squares regression with one independent variable estimating the linear model.
Here is an example:
```groovy
import se.alipsa.groovy.stats.regression.LinearRegression

def x = [2.7, 3, 5, 7, 9, 11, 14]
def y = [4, 5, 7, 10.8, 15, 20, 40]
def model = new LinearRegression(x, y)

// The same thing can be done from a Matrix i.e:
// def table = Matrix.create(
//    x: [2.7, 3, 5, 7, 9, 11, 14],
//    y: [4, 5, 7, 10.8, 15, 20, 40]
// )
// model = new LinearRegression(table, 'x', 'y')

// note: 8 is is the number of decimals to round the result to
assert -6.23971066 == model.getIntercept(8) 
assert 2.81388732 == model.getSlope(8)
assert 30.34082454 == model.predict(13, 8)
```