package org.jarvis.code.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.Promotion;
import org.jarvis.code.ui.custom.ColorView;
import org.jarvis.code.ui.custom.DialogView;
import org.jarvis.code.ui.register.RegisterFragment;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KimChheng on 6/2/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    private final int VIEW_PRODUCT = 0;
    private final int VIEW_PROMOTION = 1;
    private final int VIEW_LOADING = 2;

    private List<Product> originalList;
    private ArrayMap<Integer, Product> copyList;
    private Context context;

    private static String imgUrl = Constants.BASE_URL + "mobile/image/view/";

    public ProductAdapter(Context context, List<Product> products) {
        this.originalList = products;
        this.context = context;
        this.copyList = new ArrayMap();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_PRODUCT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return this.new ProductViewHolder(view);
        } else if (viewType == VIEW_PROMOTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
            return this.new PromotionViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            return this.new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            Product product = originalList.get(position);
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.product = product;
            productViewHolder.lblCode.setText(context.getResources().getString(R.string.string_code) + product.getCode());
            productViewHolder.colorView.renderColor(product.getColors());
            productViewHolder.lblSize.setText(context.getResources().getString(R.string.string_size) + product.getSize());
            productViewHolder.lblPrice.setText(context.getResources().getString(R.string.string_price) + product.getPrice());
            productViewHolder.lblContact.setText(context.getResources().getString(R.string.string_contact) + product.getContact().getPhone1());
            Picasso.with(context).load(imgUrl + product.getImages().get(0))
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(productViewHolder.image);
        } else if (holder instanceof PromotionViewHolder) {
            Promotion promotion = (Promotion) originalList.get(position);
            PromotionViewHolder promotionViewHolder = (PromotionViewHolder) holder;
            Picasso.with(context).load(imgUrl + promotion.getImages().get(0))
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(promotionViewHolder.image);
        } else {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return originalList == null ? 0 : originalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Product item = originalList.get(position);
        if (item != null) {
            if (item instanceof Promotion)
                return VIEW_PROMOTION;
            else
                return VIEW_PRODUCT;
        } else
            return VIEW_LOADING;
    }

    public void addAll(List<Product> products) {
        originalList.addAll(products);
        putAll(products);
    }

    public void add(Product product) {
        originalList.add(product);
        put(product);
    }

    public void add(int index, Product product) {
        originalList.add(index, product);
        put(product);
    }

    public void put(int key, Product value) {
        originalList.add(value);
        copyList.put(key, value);
    }

    private void put(Product product) {
        if (product != null)
            copyList.put(product.getId(), product);
       /*else
            copyList.put(size() - 1, product);*/
    }

    private void putAll(List<Product> products) {
        for (Product product : products) {
            if (product != null)
                copyList.put(product.getId(), product);
        }
    }

    public void remove(int index) {
        Product product = originalList.remove(index);
        if (product != null)
            copyList.remove(product.getId());
    }

    public void clear() {
        originalList.clear();
        copyList.clear();
    }

    public int size() {
        return originalList.size();
    }

    public boolean isEmpty() {
        return originalList.isEmpty();
    }

    public void filter(String text) {
        Loggy.i(ProductAdapter.class, "Search product '" + text + "'");
        originalList.clear();
        if (!text.isEmpty()) {
            for (Product product : copyList.values()) {
                if (product instanceof Product) {
                    if (product.getCode().toLowerCase().contains(text) ||
                            product.getColor().toLowerCase().contains(text) ||
                            product.getPrice().toLowerCase().contains(text) ||
                            product.getSize().toLowerCase().contains(text)) {
                        originalList.add(product);
                    }
                }
            }
        } else {
            originalList.addAll(copyList.values());
        }
        notifyDataSetChanged();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.lblCode)
        TextView lblCode;
        @BindView(R.id.lblPrice)
        TextView lblPrice;
        @BindView(R.id.lblSize)
        TextView lblSize;
        @BindView(R.id.lblContact)
        TextView lblContact;
        @BindView(R.id.btn_register)
        AppCompatButton btnRegister;
        @BindView(R.id.img_view_product)
        ImageView image;
        @BindView(R.id.colorView)
        ColorView colorView;
        private Product product;


        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnRegister.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            if (view.getId() == R.id.btn_register) {
                RegisterFragment registerFragment = RegisterFragment.newInstance(product);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, registerFragment)
                        .addToBackStack("registerFragment")
                        .commit();
            } else {
                DialogView dialogView = DialogView.newInstance(product);
                dialogView.show(fragmentManager.beginTransaction(), "Image Gallery");
                //dialogView.setCancelable(false);
            }
        }
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_view_promotion)
        ImageView image;

        public PromotionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
