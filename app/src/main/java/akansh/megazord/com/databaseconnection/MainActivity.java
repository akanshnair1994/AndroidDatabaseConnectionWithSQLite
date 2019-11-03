package akansh.megazord.com.databaseconnection;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id, name, brand, cost, qty;
    AppCompatButton add, update, remove, view;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.productID);
        name = findViewById(R.id.productName);
        brand = findViewById(R.id.productBrand);
        cost = findViewById(R.id.productPrice);
        qty = findViewById(R.id.productQuantity);
        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        remove = findViewById(R.id.remove);
        view = findViewById(R.id.view);

        dbHelper = new DatabaseHelper(MainActivity.this, "", null, 0);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = addProduct(name.getText().toString(), brand.getText().toString(), Double.parseDouble(cost.getText().toString()), Integer.parseInt(qty.getText().toString()));

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "The records were successfully entered..", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    brand.setText("");
                    cost.setText("");
                    qty.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Some problem occurred while adding. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = updateProduct(id.getText().toString(), name.getText().toString(), brand.getText().toString(), Double.parseDouble(cost.getText().toString()), Integer.parseInt(qty.getText().toString()));

                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "The records were successfully updated..", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    brand.setText("");
                    cost.setText("");
                    qty.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Some problem occurred while updating. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleted = deleteProduct(id.getText().toString());

                if (deleted > 0) {
                    Toast.makeText(MainActivity.this, "The records were successfully updated..", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    brand.setText("");
                    cost.setText("");
                    qty.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Some problem occurred while deleting. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = viewProducts();

                if (c.getCount() == 0) {
                    showMsg("error", "No Products Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("id, " + c.getString(0) + "\n");
                    buffer.append("name, " + c.getString(1) + "\n");
                    buffer.append("brand, " + c.getString(2) + "\n");
                    buffer.append("cost, " + c.getString(3) + "\n");
                    buffer.append("qty, " + c.getString(4) + "\n");
                }

                showMsg("data", buffer.toString());
            }
        });
    }

    private void showMsg(String msgType, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(msgType);
        builder.setMessage(data);
        builder.show();
    }

    private boolean addProduct(String name, String brand, Double cost, int qty) {
        return dbHelper.addProducts(name, brand, cost, qty);
    }

    private boolean updateProduct(String id, String name, String brand, Double cost, int qty) {
        return dbHelper.updateProducts(id, name, brand, cost, qty);
    }

    private int deleteProduct(String id) {
        return dbHelper.deleteProduct(id);
    }

    private Cursor viewProducts() {
        return dbHelper.viewAllProducts();
    }
}
