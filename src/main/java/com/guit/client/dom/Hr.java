package com.guit.client.dom;

import com.guit.client.Implementation;
import com.guit.client.dom.impl.HrImpl;
import com.guit.client.junit.Mock;
import com.guit.junit.dom.HrMock;

@Implementation(HrImpl.class)
@Mock(HrMock.class)
public interface Hr extends Element {
}