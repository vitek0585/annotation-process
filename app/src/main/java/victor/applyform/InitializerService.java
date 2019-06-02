package victor.applyform;

import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InitializerService {

    public <TViewModel> void initView(View view, TViewModel vm) {
        for (Field field : vm.getClass().getFields()) {
            try {
                initViewElement(vm, field, view);
                initViewGroup(vm, field, view);
                initViewOptionalGroup(vm, field, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public <TViewModel> void initViewModel(TViewModel viewModel, View view) {
        initViewModelInternal(viewModel, viewModel.getClass().getFields(), view);
    }

    // region init view
    private <TViewModel> void initViewModelInternal(TViewModel viewModel, Field[] fields, View view) {
        for (Field field : fields) {
            try {
                initProperty(viewModel, field, view);
                initGroupProperty(viewModel, view, field);
                initOptionalGroupProperty(viewModel, view, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private <TViewModel> void initViewElement(TViewModel vm, Field field, View view) throws Exception {
        Identity annotation = field.getAnnotation(Identity.class);
        ValueType formItem = field.getAnnotation(ValueType.class);

        if (annotation == null || formItem == null) return;
        View elem = view.findViewById(annotation.id());
        if (elem == null) return;

        switch (formItem.type()) {
            case text: {
                ((EditText) elem).setText(String.valueOf(field.get(vm)));
                break;
            }
            case date: {
                // convert to date time
                elem.setTag(field.get(vm));
                ((EditText) elem).setText(String.valueOf(field.get(vm)));
                break;
            }
            case dropdown: {
                ArrayValue array = field.getAnnotation(ArrayValue.class);
                if(array == null) return;

                field.getAnnotation(ArrayValue.class);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(view.getContext(),
                        android.R.layout.simple_spinner_item,
                        view.getContext().getResources().getStringArray(array.resId()));

                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                AppCompatSpinner spinner = (AppCompatSpinner) elem;
                spinner.setAdapter(spinnerArrayAdapter);
                spinner.setSelection(field.getInt(vm));
                break;
            }
        }
    }

    private <TViewModel> void initViewGroup(TViewModel vm, Field field, View view) throws Exception {
        IdentityGroup formArea = field.getAnnotation(IdentityGroup.class);
        if (formArea == null) return;

        initView(view, field.get(vm));
    }

    private <TViewModel> void initViewOptionalGroup(TViewModel vm, Field field, View view) throws Exception {
        IdentityOptionalGroup formOptionalArea = field.getAnnotation(IdentityOptionalGroup.class);
        if (formOptionalArea == null) return;
        LinearLayout viewById = view.findViewById(formOptionalArea.id());
        if (viewById == null) return;

        List list = (List) field.get(vm);
        for (int i = 0; i < list.size(); i++) {
            View childAt = viewById.getChildAt(i);
            initView(childAt, list.get(i));
        }
    }
    // endregion

    // region init ViewModel
    private <TViewModel> void initGroupProperty(TViewModel viewModel, View view, Field field) throws Exception {
        IdentityGroup group = field.getAnnotation(IdentityGroup.class);
        if (group == null) return;

        Object instance = field.getType().getConstructor().newInstance();
        initViewModelInternal(instance, field.getType().getFields(), view);
        field.set(viewModel, instance);
    }

    private <TViewModel> void initOptionalGroupProperty(TViewModel viewModel, View view, Field field) throws Exception {
        IdentityOptionalGroup group = field.getAnnotation(IdentityOptionalGroup.class);
        if (group == null) return;
        LinearLayout layout = view.findViewById(group.id());
        if (layout == null) return;

        List list = new ArrayList<>();
        Type genericType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        Field[] fieldsArea = ((Class) genericType).getFields();
        for (int i = 0; i < layout.getChildCount(); i++) {
            Object instance = ((Class) genericType).getConstructor().newInstance();
            initViewModelInternal(instance, fieldsArea, layout.getChildAt(i));
            list.add(instance);
        }

        if (!list.isEmpty()) field.set(viewModel, list);
    }

    private <TViewModel> void initProperty(TViewModel viewModel, Field field, View view) throws Exception {
        Identity identity = field.getAnnotation(Identity.class);
        ValueType valueType = field.getAnnotation(ValueType.class);
        if (identity == null || valueType == null) return;
        View elem = view.findViewById(identity.id());
        if (elem == null) return;

        switch (valueType.type()) {
            case text: {
                field.set(viewModel, ((EditText) elem).getText().toString());
                break;
            }
            case date: {
                field.set(viewModel, elem.getTag());
                break;
            }
            case dropdown: {
                field.setInt(viewModel, (int) ((AppCompatSpinner) elem).getSelectedItemId());
                break;
            }
        }
    }
    // endregion
}
