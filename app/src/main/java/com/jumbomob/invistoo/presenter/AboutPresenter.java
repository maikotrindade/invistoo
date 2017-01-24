package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.model.dto.LicenseDTO;
import com.jumbomob.invistoo.view.AboutView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class AboutPresenter implements BasePresenter<AboutView> {

    private AboutView mView;

    @Override
    public void attachView(AboutView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public AboutPresenter(AboutView view) {
        this.mView = view;
    }

    public List<LicenseDTO> getLicenses() {

        List<LicenseDTO> licenses = new ArrayList<>();

        licenses.add(new LicenseDTO("Retrofit", "2.0.0", "Copyright 2013 Square, Inc.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        licenses.add(new LicenseDTO("Okhttp logging-interceptor", "2.7.5",
                "   Copyright (C) 2015 Square, Inc.\n" +
                "  \n" +
                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "   you may not use this file except in compliance with the License.\n" +
                "   You may obtain a copy of the License at\n" +
                "  \n" +
                "        http://www.apache.org/licenses/LICENSE-2.0\n" +
                "  \n" +
                "   Unless required by applicable law or agreed to in writing, software\n" +
                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "   See the License for the specific language governing permissions and\n" +
                "   limitations under the License.\n" +
                "  "));

        licenses.add(new LicenseDTO("Support Design", "23.4.0", "  Copyright (C) 2016 The Android Open Source Project\n" +
                "\n" +
                "  Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "  you may not use this file except in compliance with the License.\n" +
                "  You may obtain a copy of the License at\n" +
                " \n" +
                "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                " \n" +
                "  Unless required by applicable law or agreed to in writing, software\n" +
                "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "  See the License for the specific language governing permissions and\n" +
                "  limitations under the License."));

        licenses.add(new LicenseDTO("MPAndroidChart", "v2.2.4",
                "   Copyright (C) 2016 Philipp Jahoda\n" +
                "  \n" +
                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "   you may not use this file except in compliance with the License.\n" +
                "   You may obtain a copy of the License at\n" +
                "  \n" +
                "        http://www.apache.org/licenses/LICENSE-2.0\n" +
                "  \n" +
                "   Unless required by applicable law or agreed to in writing, software\n" +
                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "   See the License for the specific language governing permissions and\n" +
                "   limitations under the License.\n" +
                "  "));

        licenses.add(new LicenseDTO("FloatingActionButton", "2", "Copyright 2015 Dmytro Tarianyk\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        licenses.add(new LicenseDTO("firebase-client-android", "2", "Copyright 2016 Google Inc. All Rights Reserved.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS-IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.\n" +
                "\n" +
                "This is not an official Google product."));

        licenses.add(new LicenseDTO("joda", "2.9.4.2",
                "   Copyright 2015 Dan Lew\n" +
                "  \n" +
                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "   you may not use this file except in compliance with the License.\n" +
                "   You may obtain a copy of the License at\n" +
                "  \n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "  \n" +
                "   Unless required by applicable law or agreed to in writing, software\n" +
                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "   See the License for the specific language governing permissions and\n" +
                "   limitations under the License.\n" +
                "  "));

        return licenses;
    }
}