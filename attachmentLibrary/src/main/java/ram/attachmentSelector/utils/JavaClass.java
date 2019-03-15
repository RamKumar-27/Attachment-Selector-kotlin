package ram.attachmentSelector.utils;

import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.SingleSource;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import ram.attachmentSelector.data.model.HeaderItemModel;
import ram.attachmentSelector.data.model.ImageDataModel;

public class JavaClass {
    public static void convertRxJava(List<ImageDataModel> list) {

        HashMap<Date, List<ImageDataModel>> maps = new HashMap<>();

        Flowable.just(list).flatMapIterable(new Function<List<ImageDataModel>, Iterable<ImageDataModel>>() {
            @Override
            public Iterable<ImageDataModel> apply(List<ImageDataModel> list) throws Exception {
                return list;
            }
        }).groupBy(new Function<ImageDataModel, Date>() {
            @Override
            public Date apply(ImageDataModel imageDataModel) throws Exception {
                return imageDataModel.getDate();
            }
        }).flatMapSingle(new Function<GroupedFlowable<Date, ImageDataModel>, SingleSource<List<ImageDataModel>>>() {
            @Override
            public SingleSource<List<ImageDataModel>> apply(GroupedFlowable<Date, ImageDataModel> dateImageDataModelGroupedFlowable) throws Exception {
                return dateImageDataModelGroupedFlowable.toList();
            }
        }).toMap(new Function<List<ImageDataModel>, String>() {
            @Override
            public String apply(List<ImageDataModel> list) throws Exception {
                ImageDataModel modelItem = list.get(0);
                HeaderItemModel item = new HeaderItemModel(modelItem.getDateCreated(), modelItem.getDate(), false);
                return modelItem.getDateCreated();
            }
        }).subscribe(new BiConsumer<Map<String, List<ImageDataModel>>, Throwable>() {
            @Override
            public void accept(Map<String, List<ImageDataModel>> stringListMap, Throwable throwable) throws Exception {
                Log.e("ddd", "ddd");
                Log.e("ddd", stringListMap.size() + "");
            }
        });
//                .subscribe(new SingleObserver<Map<String, List<ImageDataModel>>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("ddd","ddd");
//            }
//
//            @Override
//            public void onSuccess(Map<String, List<ImageDataModel>> stringListMap) {
//
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("eee","eee");
//
//            }
//        });
    }

}
