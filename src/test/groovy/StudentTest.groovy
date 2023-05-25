import org.junit.jupiter.api.Test
import se.alipsa.groovy.datasets.*
import se.alipsa.groovy.stats.Student

import static org.junit.jupiter.api.Assertions.*

class StudentTest {

  /**
   * Equivalent code in R
   * <code><pre>
   * flowers <- iris[iris$Species == 'setosa' | iris$Species == 'virginica',]
   * print(t.test(Petal.Length ~ Species, data = flowers))
   * </pre></code>
   */
  @Test
  void testTwoSample() {
    def iris = Dataset.iris()
    def speciesIdx = iris.columnIndex("Species")
    def setosa = iris.subset {
      it[speciesIdx] == 'setosa'
    }
    def virginica = iris.subset {
      it[speciesIdx] == 'virginica'
    }
    Student.Result result = Student.twoSample(setosa['Petal Length'], virginica['Petal Length'])
    assertEquals(-49.96570336, result.getT(8))
  }
}
