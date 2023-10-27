package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.domain.CarPark;
import racingcar.service.impl.InputConvertServiceImpl;
import racingcar.service.impl.ValidateServiceImpl;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 전진_정지() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
            },
            MOVING_FORWARD, STOP
        );
    }

    @Test
    void 이름에_대한_예외_처리() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 마지막_글자가_쉼표_일때_예외_처리(){
        String input = "kyh,";
        ValidateServiceImpl validateService = new ValidateServiceImpl();
        InputConvertServiceImpl inputConvertService = new InputConvertServiceImpl(validateService);

        Assertions.assertThatThrownBy(()->inputConvertService.inputConvertCarPark(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Car_이름_제대로_들어갔는지_체크() {
        String input = "cbnu,math,eng";
        ValidateServiceImpl validateService = new ValidateServiceImpl();
        InputConvertServiceImpl inputConvertService = new InputConvertServiceImpl(validateService);
        CarPark carPark = inputConvertService.inputConvertCarPark(input);

        List<String> nameList = carPark.makeNameList();
        System.out.println("nameList = " + nameList);
        assertThat(nameList).contains("cbnu", "math","eng");
        assertThat(nameList).containsExactly("cbnu", "math","eng");
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
