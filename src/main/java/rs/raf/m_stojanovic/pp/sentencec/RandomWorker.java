package rs.raf.m_stojanovic.pp.sentencec;

import java.util.Random;

public class RandomWorker implements Worker {

    private final Random random = new Random();

    @Override
    public Worker.WorkResult work(String code) {
        int randomInt = random.nextInt(200);
        if (randomInt >= 100)
            return new WorkResult.GoodResult("" + randomInt);
        return new WorkResult.GoodResult(code, randomInt);
    }

}
