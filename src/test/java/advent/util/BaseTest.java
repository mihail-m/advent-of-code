package advent.util;

import advent.base.Task;
import org.junit.jupiter.api.Assertions;

import static advent.util.TestUtil.RESULT_MESSAGE;
import static advent.util.TestUtil.postAndValidateResult;

public abstract class BaseTest<TaskType extends Task<?, SolutionType>, SolutionType> {

    protected void sampleTestTask1(TaskType task, SolutionType expectedResult) {
        Assertions.assertEquals(expectedResult, task.getResult());
    }

    protected void sampleTestTask2(TaskType task, SolutionType expectedResult) {
        Assertions.assertEquals(expectedResult, task.getResult());
    }

    protected void testTask1(TaskType task) {
        System.out.printf(RESULT_MESSAGE, "1", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "1");
    }

    protected void testTask2(TaskType task) {
        System.out.printf(RESULT_MESSAGE, "2", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "2");
    }
}
