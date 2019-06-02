package victor.applyform;

import android.view.View;

import java.lang.reflect.Field;

class ViewHolder<TViewModel> {

    InitializerService _initializerService;
    View rootView;

    void init(View view) {
        _initializerService = new InitializerService();
        rootView = view;

        for (Field field : ViewHolder.class.getFields()) {
            Identity annotation = field.getAnnotation(Identity.class);

            if (annotation == null) continue;

            try {
                field.setAccessible(true);
                View viewById = rootView.findViewById(annotation.id());
                if (viewById != null) {
                    field.set(this, viewById);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    void set(TViewModel vm) {
        _initializerService.initView(rootView, vm);
    }

    public ViewModel getData() {
        ViewModel viewModel = new ViewModel();
        _initializerService.initViewModel(viewModel, rootView);
        return viewModel;
    }


}
