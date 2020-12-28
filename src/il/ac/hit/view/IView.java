package il.ac.hit.view;

import il.ac.hit.viewModel.IViewModel;

public interface IView {
    public void start();
    public void setViewModel(IViewModel vm);
    public void showGoodMassage(String text);
    public void showBadMassage(String text);
}
