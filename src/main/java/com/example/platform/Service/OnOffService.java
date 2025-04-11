//package com.example.platform.Service;
//
//
//import com.example.platform.Model.OnOffModel;
//import com.example.platform.Repository.OnOffRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OnOffService {
//
//    @Autowired
//    private OnOffRepository onOffRepository;
//
//    public String getStatus(Long id){
//
//
//        OnOffModel check = onOffRepository.findById(id).orElseThrow(() -> new RuntimeException("id Not Found"));
//
//        if (check.isOpen()){
//            return "IsOpen";
//        }
//        else {
//            return "IsClosed";
//        }
//    }
//
//
//    public String changeStatus(Long id){
//
//
//        OnOffModel check = onOffRepository.findById(id).orElseThrow(() -> new RuntimeException("id Not Found"));
//
//        if (check.isOpen()){
//            OnOffModel onOffModel = new OnOffModel();
//            onOffModel.setOpen(false);
//            onOffRepository.save(onOffModel);
//            return "IsClosed";
//        }
//        else {
//            OnOffModel onOffModel = new OnOffModel();
//            onOffModel.setOpen(true);
//            onOffRepository.save(onOffModel);
//            return "IsOpen";
//        }
//    }
//}
