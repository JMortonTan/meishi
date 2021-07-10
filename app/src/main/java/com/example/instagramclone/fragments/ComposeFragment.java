package com.example.instagramclone.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends Fragment {
    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;

    NfcAdapter nfcAdapter;
    // Flag to indicate that Android Beam is available
    boolean androidBeamAvailable  = false;

//    private TextView tvGreeting;
//    private EditText etDescription;
//    private Button btnCaptureImage;
//    private ImageView ivPostImage;
//    private Button btnSubmit;
//    private Button btnLogout;

//    private File photoFile;
//    public String photoFileName = "photo.jpg";

    // List of URIs to provide to Android Beam
    private Uri[] fileUris = new Uri[10];
    private FileUriCallback fileUriCallback;
    //...
    /**
     * Callback that Android Beam file transfer calls to get
     * files to share
     */
    private class FileUriCallback implements
            NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {
        }
        /**
         * Create content URIs as needed to share with another device
         */
        @Override
        public Uri[] createBeamUris(NfcEvent event) {
            return fileUris;
        }
    }

    public ComposeFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        super.onViewCreated(view, savedInstanceState);

        // NFC isn't available on the device
        if (!this.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            /*
             * Disable NFC features here.
             * For example, disable menu items or buttons that activate
             * NFC-related features
             */
            //...
            // Android Beam file transfer isn't supported
            Toast.makeText(getContext(), "NFC IS NOT AVAIL ON DEVICE", Toast.LENGTH_LONG).show();
        } else if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // If Android Beam isn't available, don't continue.
            androidBeamAvailable = false;
            /*
             * Disable Android Beam file transfer features here.
             */
            //...
            // Android Beam file transfer is available, continue
            Toast.makeText(getContext(), "BEAM IS NOT AVAIL ON DEVICE", Toast.LENGTH_LONG).show();
        } else {
            androidBeamAvailable = true;
            nfcAdapter = NfcAdapter.getDefaultAdapter(this.getActivity());
            Toast.makeText(getContext(), "ALL SYSTEMS GO", Toast.LENGTH_LONG).show();

            // Android Beam file transfer is available, continue
            //...
            nfcAdapter = NfcAdapter.getDefaultAdapter(this.getActivity());
            /*
             * Instantiate a new FileUriCallback to handle requests for
             * URIs
             */
            fileUriCallback = new FileUriCallback();
            // Set the dynamic callback for URI requests.
            nfcAdapter.setBeamPushUrisCallback(fileUriCallback,this.getActivity());

            Uri[] fileUris = new Uri[10];
            String transferFile = "transferimage.jpg";
            File extDir = this.getActivity().getExternalFilesDir(null);
            File requestFile = new File(extDir, transferFile);
            requestFile.setReadable(true, false);
            // Get a URI for the File and add it to the list of URIs
            Uri fileUri = Uri.fromFile(requestFile);
            if (fileUri != null) {
                fileUris[0] = fileUri;
            } else {
                Log.e("My Activity", "No File URI available for file.");
            }
            Toast.makeText(getContext(), "FIN", Toast.LENGTH_LONG).show();
        //...
        }

//        tvGreeting = view.findViewById(R.id.tvGreeting);
//        etDescription = view.findViewById(R.id.etDescription);
//        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
//        ivPostImage = view.findViewById(R.id.ivPostImage);
//        btnSubmit = view.findViewById(R.id.btnSubmit);
//        btnLogout = view.findViewById(R.id.btnLogout);
//        tvGreeting.setText("Hello! " + ParseUser.getCurrentUser().getUsername());



//        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchCamera();
//
//            }
//        });
//
//        //queryPosts();
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String description = etDescription.getText().toString();
//                if (description.isEmpty()) {
//                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (photoFile == null || ivPostImage.getDrawable() == null) {
//                    Toast.makeText(getContext(), "There is no image!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ParseUser currentUser = ParseUser.getCurrentUser();
//                savePost(description, currentUser, photoFile);
//            }
//        });
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseUser.logOut();
//                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
//                goLoginActivity();
//            }
//        });
    }
//
//    private void launchCamera() {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference for future access
//        photoFile = getPhotoFileUri(photoFileName);
//
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                ivPostImage.setImageBitmap(takenImage);
//            } else { // Result was a failure
//                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        return new File(mediaStorageDir.getPath() + File.separator + fileName);
//    }
//
//    private void savePost(String description, ParseUser currentUser, File photoFile) {
//        Post post = new Post();
//        post.setDescription(description);
//        post.setImage(new ParseFile(photoFile));
//        post.setUser(currentUser);
//        post.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Error while saving", e);
//                    Toast.makeText(getContext(),"Error while saving", Toast.LENGTH_SHORT).show();
//                }
//
//                Log.i(TAG,"Post save was successful!!");
//                etDescription.setText("");
//                ivPostImage.setImageResource(0);
//                Toast.makeText(getContext(),"Post Saved!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//    private void goLoginActivity() {
//        Intent i = new Intent(getContext(), LoginActivity.class);
//        startActivity(i);
//        //finish();
//    }
}