package com.example.assessment.service;

import com.example.assessment.repository.AnswerRepository;
import com.example.assessment.repository.QuestionRepository;
import com.example.assessment.repository.SetInfoRepository;
import com.example.assessment.dto.ResponseAnswerDto;
import com.example.assessment.dto.ResponseQuestionDto;
import com.example.assessment.dto.ResponseSetDto;
import com.example.assessment.dto.SetDto;
import com.example.assessment.exception.QuestionIdNotFoundException;
import com.example.assessment.exception.SetIdNotFoundException;
import com.example.assessment.exception.SetNameNotFoundException;
import com.example.assessment.model.OptionModel;
import com.example.assessment.model.Question;
import com.example.assessment.model.SetInfo;
import com.example.assessment.model.SetStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AssessmentService {

    private final SetInfoRepository setInfoRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AssessmentService(SetInfoRepository setInfoRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.setInfoRepository = setInfoRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    private ResponseAnswerDto mapAnswerToDto(OptionModel answer, Integer questionId) {
        ResponseAnswerDto responseAnswerDto = new ResponseAnswerDto();
        responseAnswerDto.setQuestionId(questionId);
        responseAnswerDto.setAnswer(answer.getAnswer());
        responseAnswerDto.setSuggestion(answer.getSuggestion());
        return responseAnswerDto;
    }

    private ResponseQuestionDto mapQuestionToDto(Question question, Integer setId) {
        ResponseQuestionDto responseQuestionDto = new ResponseQuestionDto();
        responseQuestionDto.setSetId(setId);
        responseQuestionDto.setQuestionId(question.getQuestionId());
        responseQuestionDto.setQuestion(question.getQuestionText());
        if (question.getAnswers()==null){
            responseQuestionDto.setAnswers(null);
        }else {
            List<ResponseAnswerDto> responseAnswers = question.getAnswers().stream()
                    .map(answer -> mapAnswerToDto(answer, question.getQuestionId()))
                    .toList();

            responseQuestionDto.setAnswers(responseAnswers);
        }
        return responseQuestionDto;
    }

    private ResponseSetDto mapSetInfoToDto(SetInfo setInfo) {
        ResponseSetDto responseSetDto = new ResponseSetDto();
        responseSetDto.setSetId(setInfo.getSetId());
        responseSetDto.setSetName(setInfo.getSetName());
        responseSetDto.setCreatedBy(setInfo.getCreatedBy());
        responseSetDto.setCreatedAt(setInfo.getCreatedAt());
        responseSetDto.setModifiedAt(setInfo.getModifiedAt());
        responseSetDto.setDomain(setInfo.getDomain());
        responseSetDto.setStatus(String.valueOf(setInfo.getStatus()));

        List<ResponseQuestionDto> responseQuestions = setInfo.getQuestions().stream()
                .map(question -> mapQuestionToDto(question, setInfo.getSetId()))
                .toList();

        responseSetDto.setQuestions(responseQuestions);
        return responseSetDto;
    }

    public List<Question> getSetBySetName(String setname)  {
        Optional<SetInfo> setInfos = setInfoRepository.findBySetName(setname);

        if (setInfos.isPresent()) {
            SetInfo setInfo = setInfos.get();

            for (Question question : setInfo.getQuestions()) {
                question.setAnswers(null);

            }

            return setInfo.getQuestions();

        }
        else {
            throw new SetNameNotFoundException(setname);
        }
    }
    public ResponseSetDto saveSetInfo(SetInfo setInfo) {
        setInfo.setStatus(SetStatus.PENDING);
        for (Question question : setInfo.getQuestions()) {
            for (OptionModel answer : question.getAnswers()) {
                answerRepository.save(answer);
            }
            questionRepository.save(question);
        }
        setInfoRepository.save(setInfo);

        return mapSetInfoToDto(setInfo);
    }



    public List<SetDto> getAllSet() {

        return mapSetInfoListToSetDtoList(setInfoRepository.findAll());
    }

    public List<SetDto> mapSetInfoListToSetDtoList(List<SetInfo> setInfoList) {
        return setInfoList.stream()
                .map(this::mapSetInfoToSetDto)
                .toList();
    }

    private SetDto mapSetInfoToSetDto(SetInfo setInfo) {
        return new SetDto(
                setInfo.getSetId(),
                setInfo.getSetName(),
                setInfo.getCreatedBy(),
                setInfo.getDomain(),
                setInfo.getStatus()
        );
    }

    public ResponseQuestionDto modifySetQuestionInfo(Integer setId, Integer questionId, List<OptionModel> options) {

        Optional<SetInfo> setInfo = setInfoRepository.findById(setId);
        Optional<Question> currentQuestion = questionRepository.findById(questionId);
        if (currentQuestion.isPresent() && setInfo.isPresent()){
            currentQuestion.get().setAnswers(options);
            ResponseQuestionDto question = mapQuestionToDto(questionRepository.save(currentQuestion.get()),setId);
            return question;

        }
        else if (setInfo.isEmpty()) {
            throw new SetIdNotFoundException(setId);
        } else {
            throw new QuestionIdNotFoundException(questionId);
        }
    }

    public void deleteQuestionFromAssessment(Integer setId, Integer questionId) {
        Optional<SetInfo> setInfo = setInfoRepository.findById(setId);
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent() && setInfo.isPresent()){
            questionRepository.deleteById(questionId);
        } else if (setInfo.isEmpty()) {
            throw new SetIdNotFoundException(setId);
        } else {
            throw new QuestionIdNotFoundException(questionId);
        }

    }

    public ResponseSetDto getSetBySetId(Integer setId) {
        Optional<SetInfo> setInfos = setInfoRepository.findById(setId);

        if (setInfos.isPresent()) {
            SetInfo setInfo = setInfos.get();
            ResponseSetDto responseSetDto = mapSetInfoToDto(setInfo);

            return responseSetDto;

        }
        else {
            throw new SetIdNotFoundException(setId);
        }

    }
}