package com.sublime.sublimecash.sublime;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import Common.Session;
import Model.Profile;
import de.blox.treeview.TreeView;

public class TreeViewActivity extends AppCompatActivity {
    WebView webTree;

    TreeView treeView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view);
       // Login();
        requestQueue = Volley.newRequestQueue(this);
        myProfile = Session.GetProfile(getApplicationContext());
        webTree = findViewById(R.id.webTree);
        webTree.loadUrl("http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Home/search_tree2/"+myProfile.UserLogin);
        webTree.getSettings().setJavaScriptEnabled(true);
        webTree.getSettings().setDomStorageEnabled(true);
        webTree.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        myProfile = Session.GetProfile(this);
   /*     treeView = findViewById(R.id.treeview);
        requestQueue = Volley.newRequestQueue(this);
        BaseTreeAdapter adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.row_item_treenodes) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.mTextView.setText(data.toString());
            }
        };
        treeView.setAdapter(adapter);
        TreeNode rootNode = new TreeNode("Root");

        TreeNode child1 = new TreeNode("Child 1");
        TreeNode child2 = new TreeNode("Child 2");
        TreeNode child3 = new TreeNode("C_3");
        TreeNode child4 = new TreeNode("Child 4");
        TreeNode child5 = new TreeNode("C_5");
        TreeNode child6 = new TreeNode("C_6");
        TreeNode child7 = new TreeNode("C_7");
        TreeNode child8 = new TreeNode("C_8");
        TreeNode child10 = new TreeNode("Child 10");
        TreeNode child11 = new TreeNode("C_11");
        TreeNode child12 = new TreeNode("C_12");
        TreeNode child13 = new TreeNode("C_13");
        // Childs added to root
        rootNode.addChild(child1);
        rootNode.addChild(child2);

        // Childs 3 & 4 added to Child 1
        child1.addChild(child3);
        child1.addChild(child4);

        // Childs 5 & 6 added to Child 2
        child2.addChild(child5);
        child2.addChild(child6);

        // Childs 7, 8 & 9 added to Child 4
        child4.addChild(child7);
        child4.addChild(child8);

        // Childs 10 & 11 added to Child 8
        child8.addChild(child10);
        child8.addChild(child11);

        // Childs 12, 13  added to Child 10
        child10.addChild(child12);
        child10.addChild(child13);

        adapter.setRootNode(rootNode);

        //TreeView();
    }

    public class ViewHolder {

        TextView mTextView;

        ViewHolder(View view) {

            mTextView = view.findViewById(R.id.textNodes);
        }
    }

        public void TreeView () {

            progressDialog = progressDialog.show(TreeViewActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tree_Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        if (response != null) {
                            JSONObject jObj = new JSONObject(response);
                            String totalLeft = jObj.getString("total_left_active_user");
                            String totalRight = jObj.getString("total_right_active_user");
                            String main = jObj.getString("main");
                            String first_left_user = jObj.getString("first_left_user");
                            String first_right_user = jObj.getString("first_right_user");
                            String second_left_user = jObj.getString("second_left_user");
                            String second_right_user = jObj.getString("second_right_user");
                            String third_left_user = jObj.getString("third_left_user");
                            String third_right_user = jObj.getString("third_right_user");

                            TreeNode treeNode = new TreeNode();
                            treeNode.addChildren();

                        } else {
                            Toast.makeText(TreeViewActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(TreeViewActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", myProfile.UserLogin);
                    return params;
                }
            };
            requestQueue.add(stringRequest);

        } */
    }
}