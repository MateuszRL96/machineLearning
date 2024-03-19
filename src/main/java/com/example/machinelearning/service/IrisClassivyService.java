package com.example.machinelearning.service;

import org.springframework.stereotype.Service;
import org.tribuo.Dataset;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.ensemble.AdaBoostTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.Evaluation;
import org.tribuo.evaluation.Evaluator;
import org.tribuo.evaluation.TrainTestSplitter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Path;

@Service
public class IrisClassivyService {
    private Model<Label> model;

    public IrisClassivyService() throws IOException {
        trainModel();
    }

    private void trainModel() throws IOException {
        // Tworzenie instancji CSVLoader z fabryką etykiet
        CSVLoader<Label> loader = new CSVLoader<>(new LabelFactory());

        // Załaduj dane
        Path dataPath = Paths.get("src/main/resources/iris.csv");
        Dataset<Label> irisData = loader.load(dataPath, "species");

        // Podziel dane na zestaw treningowy i testowy
        TrainTestSplitter<Label> splitter = new TrainTestSplitter<>(irisData, 0.7, 1L);
        MutableDataset<Label> trainData = new MutableDataset<>(splitter.getTrain());
        MutableDataset<Label> testData = new MutableDataset<>(splitter.getTest());

        // Wybierz i wytrenuj model
        Trainer<Label> trainer = new AdaBoostTrainer();
        model = trainer.train(trainData);

        // Ewaluacja modelu
        Evaluator<Label, ?> evaluator = new Evaluator<>();
        Evaluation<Label> evaluation = evaluator.evaluate(model,testData);

        System.out.println(evaluation.toString());
    }

    public String classify(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        var factory = new LabelFactory();
        var example = new org.tribuo.Example<>(factory.generateOutput("unknown"),
                List.of(new org.tribuo.Feature("sepalLength", sepalLength),
                        new org.tribuo.Feature("sepalWidth", sepalWidth),
                        new org.tribuo.Feature("petalLength", petalLength),
                        new org.tribuo.Feature("petalWidth", petalWidth)));
        var prediction = model.predict(example);
        return prediction.getOutput().getLabel();
    }
}
